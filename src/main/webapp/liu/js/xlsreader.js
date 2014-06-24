/**
 * Created by liuzheng on 2014/6/22.
 */
var use_worker = true;
function xlsworker(data, cb) {
    var worker = new Worker('./js/xlsworker.js');
    worker.onmessage = function (e) {
        switch (e.data.t) {
            case 'ready':
                break;
            case 'e':
                console.error(e.data);
                break;
            case 'xls':
                cb(e.data.d);
                break;
        }
    };
    worker.postMessage(data);
}


function to_json(workbook) {
    var result = {};
    workbook.SheetNames.forEach(function (sheetName) {
        var roa = XLS.utils.sheet_to_row_object_array(workbook.Sheets[sheetName]);
        if (roa.length > 0) {
            result[sheetName] = roa;
        }
    });
    return result;
}

function process_wb(wb) {
    if (typeof Worker !== 'undefined') XLS.SSF.load_table(wb.SSF);
    var output = "";
    output = to_json(wb);
    console.log(output["Sheet1"]);
}

var drop = document.getElementById('drop');
function handleDrop(e) {
    e.stopPropagation();
    e.preventDefault();
    var files = e.dataTransfer.files;
    var i, f;
    for (i = 0, f = files[i]; i != files.length; ++i) {
        var reader = new FileReader();
        var name = f.name;
        reader.onload = function (e) {
            var data = e.target.result;
            if (use_worker && typeof Worker !== 'undefined') {
                xlsworker(data, process_wb);
            } else {
                var wb = XLS.read(data, {type: 'binary'});
                process_wb(wb);
            }
        };
        reader.readAsBinaryString(f);
    }
}

function handleDragover(e) {
    e.stopPropagation();
    e.preventDefault();
    e.dataTransfer.dropEffect = 'copy';
}

if (drop.addEventListener) {
    drop.addEventListener('dragenter', handleDragover, false);
    drop.addEventListener('dragover', handleDragover, false);
    drop.addEventListener('drop', handleDrop, false);
}
