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

## Upload pasted or dropped images with images_upload_handler

`images_upload_handler` receives `(blobInfo, progress)` and returns a `Promise` that resolves to the URL of the stored image. `blobInfo.blob()` returns the `Blob` and `blobInfo.filename()` returns its file name; call `progress(percent)` with a number from `0` to `100` to update HugeRTE's progress indicator:

```java
HugeRteConfig config = HugeRteConfig.defaults()
    .withPlugins(HugeRtePlugin.IMAGE)
    .withToolbar("undo redo | image")
    .with("images_upload_handler", new RawJavaScript("""
        function (blobInfo, progress) {
            return new Promise(function (resolve, reject) {
                const body = new FormData();
                body.append('file', blobInfo.blob(), blobInfo.filename());
                fetch('/upload-image', { method: 'POST', body: body })
                    .then(function (response) {
                        if (!response.ok) {
                            throw new Error('Upload failed: ' + response.status);
                        }
                        return response.json();
                    })
                    .then(function (json) { resolve(json.location); })
                    .catch(function (err) { reject(err.message); });
            });
        }"""));
```

## Reference a function from a static script

For larger callbacks, define the function in a separate JavaScript resource rendered with `JavaScriptHeaderItem` and pass its name to `RawJavaScript`:

```java
HugeRteConfig config = HugeRteConfig.defaults()
    .withPlugins(HugeRtePlugin.IMAGE)
    .with("images_upload_handler", new RawJavaScript("window.myApp.uploadImage"));
```

Keeping callback logic in a static `.js` file avoids embedding JavaScript inside Java strings and works with Wicket's strict Content Security Policy.
