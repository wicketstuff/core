---
title: Use JavaScript callbacks with RawJavaScript
---

Wrap JavaScript functions in `RawJavaScript` when setting HugeRTE options that expect a callback, such as `setup`, `file_picker_callback`, or `images_upload_handler`. Plain `String` values passed to `HugeRteConfig.with` are quoted and escaped as JavaScript string literals; `RawJavaScript` is emitted verbatim as a JavaScript expression.

> **Security warning:** `RawJavaScript` is written directly into the generated `<script>` block without any escaping — not even for `</script>`. Never build a `RawJavaScript` value from user input or untrusted data.

## Hook into the editor lifecycle with setup

The `setup` callback receives the `editor` instance before it is rendered, which lets you subscribe to editor events or register custom toolbar buttons:

```java
HugeRteConfig config = HugeRteConfig.defaults()
    .with("setup", new RawJavaScript("""
        function (editor) {
            editor.on('init', function () {
                console.log('Editor ready:', editor.id);
            });
        }"""));
```

## Pick files with file_picker_callback

Enable the `IMAGE` or `LINK` plugin, set `file_picker_types` to the dialog types you handle (space-separated values such as `"image"`, `"file"`, or `"media"`), and supply a `file_picker_callback` function accepting `(callback, value, meta)`:

```java
HugeRteConfig config = HugeRteConfig.defaults()
    .withPlugins(HugeRtePlugin.IMAGE, HugeRtePlugin.LINK)
    .withToolbar("undo redo | link image")
    .with("file_picker_types", "image")
    .with("file_picker_callback", new RawJavaScript("""
        function (callback, value, meta) {
            if (meta.filetype === 'image') {
                const input = document.createElement('input');
                input.type = 'file';
                input.accept = 'image/*';
                input.onchange = function () {
                    const file = input.files[0];
                    if (!file) {
                        return;
                    }
                    const reader = new FileReader();
                    reader.onload = function () {
                        callback(reader.result, { alt: file.name });
                    };
                    reader.readAsDataURL(file);
                };
                input.click();
            }
        }"""));
```

Call `callback(url, meta)` with the chosen URL and optional metadata such as `alt` or `title`.

## Upload pasted or dropped images

By default HugeRTE keeps pasted and dropped images as data URLs inside the HTML, so they end up in your model. To store them as files, point `images_upload_url` at a Wicket resource. HugeRTE posts each image as the multipart field `file` and expects JSON with the image's URL in `location`. Pass the resource as a `ResourceReference`, so the URL includes your context path:

```java
public class ImageUploadResource extends AbstractResource
{
    @Override
    protected ResourceResponse newResourceResponse(Attributes attributes)
    {
        var response = new ResourceResponse();
        var request = (ServletWebRequest) attributes.getRequest();
        try
        {
            var multipart = request.newMultipartWebRequest(Bytes.megabytes(5), "image-upload");
            multipart.parseFileParts();
            var file = multipart.getFile("file").get(0);
            String location;
            try (InputStream in = file.getInputStream())
            {
                location = store(file.getName(), in); // your storage; returns the image URL
            }
            response.setContentType("application/json");
            response.setWriteCallback(new WriteCallback()
            {
                @Override
                public void writeData(Attributes attributes)
                {
                    attributes.getResponse().write("{\"location\":\"" + location + "\"}");
                }
            });
        }
        catch (IOException e)
        {
            response.setError(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }
}
```

Mount it in your application and configure the editor:

```java
// in WebApplication.init()
mountResource("/image-upload", IMAGE_UPLOAD);

// IMAGE_UPLOAD is a static ResourceReference whose getResource() returns new ImageUploadResource()
HugeRteConfig config = HugeRteConfig.defaults()
    .withPlugins(HugeRtePlugin.IMAGE)
    .withToolbar("undo redo | image")
    .with("images_upload_url", IMAGE_UPLOAD);
```

The resource is reachable without a page, so check authorization in it, limit what it accepts, and decide how long stored images live. Escape `location` if it can contain `"` or `\`. HugeRTE uploads images asynchronously: an Ajax save that runs while an upload is still in progress sends the data URL instead of the final one.

For a custom transport, set `images_upload_handler` to a `RawJavaScript` function `(blobInfo, progress)` that returns a `Promise` resolving to the image URL.

## Reference a function from a static script

For larger callbacks, define the function in a separate JavaScript resource rendered with `JavaScriptHeaderItem` and pass its name to `RawJavaScript`:

```java
HugeRteConfig config = HugeRteConfig.defaults()
    .withPlugins(HugeRtePlugin.IMAGE)
    .with("images_upload_handler", new RawJavaScript("window.myApp.uploadImage"));
```

Keeping callback logic in a static `.js` file avoids embedding JavaScript inside Java strings. Both forms work under Wicket's strict Content Security Policy: inline `RawJavaScript` ends up in the nonced initialization script.
