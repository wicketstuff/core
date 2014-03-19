function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

var lang = getParameterByName('lang');
var ru =
{
// Tooltips for buttons
    "Common tools": "Общие инструменты",
    "Zoom in": "Увеличить",
    "Zoom out": "Уменьшить",
    "Show coordinates": "Показывать координаты",
    "Clear all": "Очистить все",
    "Clear traces": "Очистить следы",
    "Show information about selected element": "Информация об элементе",

    "Ruler-and-compass constructions": "Построения циркулем и линейкой",
    "Single point": "Точка",
    "Line through two points": "Прямая, проходящая через две точки",
    "Line segment connecting two points": "Отрезок, соединяющий две точки",
    "Circle with radius equals to the given segment": "Окружность с заданным радиусом",
    "Circle with given center": "Окружность с заданным центром",

    "Free hand drawing": "Рисование",
    "Insert picture": "Вставить картинку",
    "Change whiteboard background": "Изменить обои",
    "Curve": "Кривая линяя",
    "Polyline": "Ломаная линия",
    "Rectangle": "Прямоугольник",
    "Circle": "Окружность",
    "Text box": "Текст",
    "Highlight board area": "Выделить участок доски",
    "Draw an arrow": "Стрелка",
    "Highlight a point at whiteboard": "Отметить точку на доске",
    "Undo": "аннулировать",
    "Save": "экономить",
    "Document adding tools": "Документ добавление инструментов",
    "Add document to whiteboard": "Добавление документа в доску",
    "Go to previous page of the Doc": "Перейти к предыдущей странице документа",
    "Go to next page of the Doc": "Переход к следующей странице документа",
    "Cancel": "отменить",

// Controls at the info dialog
    "Click to select other element": "Нажмите для выбора следующего элемента",
    "Hide": "Скрыть",
    "Click to hide element": "Нажмите, чтобы скрыть элемент",
    "Trace": "След",
    "Color": "Цвет",
    "Click to select color": "Нажмите для выбора цвета",
    "JSON code for drawing": "JSON-код для рисунка",

// Element Labels in the info dialog
    "ClipArt does not exist": "Картинка отсутствует",
    "ClipArt from [{$label}]": "Картинка, источник {$label}",
    "Point does not exist": "Точка не сушествует",
    "Point: [{$x},{$y}]": "Точка [{$x},{$y}]",
    "Line does not exist": "Линия не существует",
    "Line [{$fromx},{$fromy}] - [{$tox},{$toy}]": "Линия от [{$fromx},{$fromy}] до [{$tox},{$toy}]",
    "Circle does not exist": "Окружность не существует",
    "Circle [{$x},{$y}] -> {$r}": "Окружность [{$x},{$y}] -> {$r}",
    "Curve does not exists": "Кривая не существует",
    "Rectangle does not exist": "Прямоугольник не существует",
    "Text does not exist": "Текст не существует",
    "Text [{$label}]": "Текст [{$label}]",
    "Underline does not exists": "Выделение не существует",
    "Underline": "Выделение",
    "Arrow does not exists": "Стрелка не существует",
    "Arrow": "Стрелка",
    "Pointer does not exists": "Указатель не существует",
    "Pointer [{$x},{$y}]": "Указатель на [{$x},{$y}]",

// Messages
    "This browser doesn''t support graphics. Please use another web browser.": "Ваш браузер не поддерживает графику. Пожалуйста, используйте другой браузер"
};