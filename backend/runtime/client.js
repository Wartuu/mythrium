function connect() {
    var ws = new WebSocket("ws://127.0.0.1:8080/ws/v1/stream?uploader=false")

    var vid = document.getElementById("stream");

    var src = new MediaSource();
    var mediaOpen = false;

    vid.controls = false;
    vid.src = URL.createObjectURL(src);

    
    src.addEventListener('sourceopen', () => {
        var buff = src.addSourceBuffer("video/webm; codecs=\"vp8\"");

        ws.binaryType = "arraybuffer";

        ws.onmessage = (e) => {
            buff.appendBuffer(e.data);
        
            if(!mediaOpen) {
                vid.play();
                mediaOpen = false;
            }
        }


    })

}