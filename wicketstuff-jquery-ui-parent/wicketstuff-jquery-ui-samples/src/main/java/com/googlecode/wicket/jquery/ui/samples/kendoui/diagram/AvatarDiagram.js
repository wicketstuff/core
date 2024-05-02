function visualTemplate(options) {
    var dataviz = kendo.dataviz;
    var group = new dataviz.diagram.Group();
    var item = options.dataItem;

    group.append(new dataviz.diagram.Rectangle({
        width: 180,
        height: 75,
        stroke: { width: 0 },
        fill: {
            gradient: {
                type: "linear",
                stops: [{ color: item.color, offset: 0, opacity: 0.5 }, { color: item.color, offset: 1, opacity: 1 }]
            }
        }
    }));

    group.append(new dataviz.diagram.TextBlock({
        x: 80,
        y: 20,
        text: item.title,
        fill: "#fff"
    }));

    group.append(new dataviz.diagram.Image({
        source: item.imageRelativePath,
        x: 3,
        y: 3,
        width: 68,
        height: 68
    }));

    return group;
}
