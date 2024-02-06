import PropTypes from "prop-types";
import '../styles/window.scss'
import { useEffect, useState } from "react";

const Window = (props) => {

    const [isDragging, setIsDragging] = useState(false);
    const [position, setPosition] = useState({x: 0, y: 0});
    const [lastPosition, setLastPosition] = useState({x: 0, y: 0});
    const [visibility, setVisibility] = useState("visible");

    const mouseDownHandler = (e) => {
        setIsDragging(true);
        setLastPosition({x: e.clientX, y: e.clientY});
    }

    const mouseUpHandler = () => {
        setIsDragging(false);
    }

    const mouseMoveHandler = (e) => {
        if(!isDragging) {
            return;
        }

        setPosition({x: position.x + lastPosition.x - e.clientX, y: position.y + lastPosition.y - e.clientY});
        setLastPosition({x: e.clientX , y: e.clientY});

    }


    useEffect(() => {
        document.addEventListener("mousemove", mouseMoveHandler);
        document.addEventListener("mouseup", mouseUpHandler);

        return () => {
            document.removeEventListener("mousemove", mouseMoveHandler);
            document.removeEventListener("mouseup", mouseUpHandler);
        }
    }, [isDragging, position, lastPosition]);


    const hideWindow = () => {
        setVisibility("hidden");
    }

    return(
        <div className="window-wrapper" style={{transform: `translate(${350 - position.x}px, ${25 - position.y}px)`, visibility: visibility}}>
            <div className="window-bar"  onMouseDown={mouseDownHandler}>
                {props.title}
                <input type="button" value={"✘"} className="window-close-button" onClick={hideWindow}/>
            
            </div>
            <div className="window-content">
                {props.element}
            </div>
        </div>
    )


}


Window.propTypes = {
    title: PropTypes.string,
    element: PropTypes.any
};

export default Window;

