function start() {
    console.log("Starting streasm");

    navigator.mediaDevices.getDisplayMedia({ video: true }).then(
        (stream) => startUpload(stream)
    ).catch((err) => {
        console.error(err);
    })


}

async function startUpload(stream) {

    var ws = new WebSocket("ws://127.0.0.1:8080/ws/v1/stream?uploader=true");
    ws.binaryData = "blob";
    ws.binaryType = "blob";
    var media = new MediaRecorder(stream);

    media.ondataavailable = (e) => {
        if(e.data.size > 0) {
            ws.send(e.data);
        }
    }

    media.onstop = () => {
        console.log("Finished");
    }

    media.start(1);
}