import React, { Suspense } from "react";
import { useParams } from "react-router-dom";
import NoteViewer from "../components/noteViewer";



const Note = () => {
    let { uuid } = useParams();


    return (
        <div id="page" className="center-flex">
            <div className="large-card no-hover">
                <NoteViewer uuid={uuid}/>
            </div>
        </div>

    )

}

export default Note;