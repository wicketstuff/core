;(function ($, undefined) {

    'use strict';

    if (typeof(WicketStuff) !== 'object') {
        window.WicketStuff = {};
    }

    if (typeof(WicketStuff.PDFJS) === 'object') {
        return;
    }

    WicketStuff.PDFJS = {
        Topic: {
            CURRENT_PAGE: 'Wicket.PDFJS.CurrentPage',
            TOTAL_PAGES: 'Wicket.PDFJS.TotalPages',
            NEXT_PAGE: 'Wicket.PDFJS.NextPage',
            PREVIOUS_PAGE: 'Wicket.PDFJS.PreviousPage',
            GOTO_PAGE: 'Wicket.PDFJS.GoToPage',
            ZOOM_IN: 'Wicket.PDFJS.ZoomIn',
            ZOOM_OUT: 'Wicket.PDFJS.ZoomOut',
        },

        init: function (config) {

            // If absolute URL from the remote server is provided, configure the CORS
            // header on that server.
            var url = config.documentUrl;

            //
            // Disable workers to avoid yet another cross-origin issue (workers need
            // the URL of the script to be loaded, and dynamically loading a cross-origin
            // script does not work).
            PDFJS.disableWorker = config.workerDisabled || false;
            PDFJS.workerSrc = config.workerUrl;

            var pdfDoc = null,
                pageNum = config.initialPage || 1,
                pageRendering = false,
                pageNumPending = null,
                scale = config.initialScale || 0.8,
                canvas = document.getElementById(config.canvasId),
                ctx = canvas.getContext('2d');

            var MIN_SCALE = 0.25;
            var MAX_SCALE = 10.0;
            var DEFAULT_SCALE_DELTA = 1.1;

            /**
             * Get page info from document, resize canvas accordingly, and render page.
             * @param num Page number.
             */
            function renderPage(num) {
                pageRendering = true;
                // Using promise to fetch the page
                pdfDoc.getPage(num).then(function(page) {
                    var viewport = page.getViewport(scale);
                    canvas.height = viewport.height;
                    canvas.width = viewport.width;
                    // Render PDF page into canvas context
                    var renderContext = {
                        canvasContext: ctx,
                        viewport: viewport
                    };
                    var renderTask = page.render(renderContext);
                    // Wait for rendering to finish
                    renderTask.promise.then(function () {
                        pageRendering = false;
                        if (pageNumPending !== null) {
                            // New page rendering is pending
                            renderPage(pageNumPending);
                            pageNumPending = null;
                        }
                    });
                    Wicket.Event.publish(WicketStuff.PDFJS.Topic.CURRENT_PAGE, pageNum, {"canvasId": config.canvasId});
                });
            }

            /**
             * If another page rendering in progress, waits until the rendering is
             * finished. Otherwise, executes rendering immediately.
             */
            function queueRenderPage(num) {
                if (pageRendering) {
                    pageNumPending = num;
                } else {
                    renderPage(num);
                }
            }

            function zoomInOnce() {
                var newScale = (scale * DEFAULT_SCALE_DELTA).toFixed(2);
                newScale = Math.ceil(newScale * 10) / 10;
                newScale = Math.min(MAX_SCALE, newScale);
                return newScale;
            }

            function zoomOutOnce() {
                var newScale = (scale / DEFAULT_SCALE_DELTA).toFixed(2);
                newScale = Math.floor(newScale * 10) / 10;
                newScale = Math.max(MIN_SCALE, newScale);
                return newScale;
            }

            function renderIfRescaled(newScale){
                if (newScale !== scale){
                    scale = newScale;
                    queueRenderPage(pageNum);
                }
            }

            /**
             * Displays previous page.
             */
            Wicket.Event.subscribe(WicketStuff.PDFJS.Topic.PREVIOUS_PAGE, function (jqEvent, data) {
                if (config.canvasId !== data.canvasId || pageNum <= 1) {
                    return;
                }
                pageNum--;
                queueRenderPage(pageNum);
            });

            /**
             * Displays next page.
             */
            Wicket.Event.subscribe(WicketStuff.PDFJS.Topic.NEXT_PAGE, function (jqEvent, data) {
                if (config.canvasId !== data.canvasId || pageNum >= pdfDoc.numPages) {
                    return;
                }
                pageNum++;
                queueRenderPage(pageNum);
            });

            /**
             * Displays selected page
             */
            Wicket.Event.subscribe(WicketStuff.PDFJS.Topic.GOTO_PAGE, function (jqEvent, data) {
                if (config.canvasId !== data.canvasId) {
                    return;
                }
                if (!data.page || data.page > pdfDoc.numPages || data.page < 1 || data.page === pageNum) {
                    return;
                }
                pageNum = data.page;
                queueRenderPage(pageNum);
            });

            /**
              * Zoom in current page
              */
            Wicket.Event.subscribe(WicketStuff.PDFJS.Topic.ZOOM_IN, function (jqEvent, data) {
                 if (config.canvasId !== data.canvasId) {
                    return;
                 }
                 renderIfRescaled(zoomInOnce());
            });

             /**
               * Zoom out current page
               */
             Wicket.Event.subscribe(WicketStuff.PDFJS.Topic.ZOOM_OUT, function (jqEvent, data) {
                  if (config.canvasId !== data.canvasId) {
                     return;
                  }
                  renderIfRescaled(zoomOutOnce());
             });

            /**
             * Asynchronously downloads PDF.
             */
            PDFJS.getDocument(url).then(function (pdfDoc_) {
                pdfDoc = pdfDoc_;
                Wicket.Event.publish(WicketStuff.PDFJS.Topic.TOTAL_PAGES, pdfDoc.numPages, {"canvasId": config.canvasId});
                renderPage(pageNum);
            });
        }
    };
})(jQuery);
