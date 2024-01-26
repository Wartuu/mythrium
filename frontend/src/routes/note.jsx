import React, { Suspense } from "react";
import { useParams } from "react-router-dom";
import Spinner from "../components/spinner";



const Note = () => {
    let { uuid } = useParams();

    const NoteViewer = React.lazy(()=>import("../components/noteViewer"));

    return (
        <div id="page" className="center-flex">
            <div className="large-card no-hover">
                <div>
                    <Suspense fallback={<Spinner/>}>
                        <NoteViewer uuid={uuid}/>
                    </Suspense>
                </div>
            </div>
        </div>

    )

}

export default Note;