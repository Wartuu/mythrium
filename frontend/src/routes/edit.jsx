import React from "react";

const NoteEditor = () => import("../components/editor");


class EditPage extends React.Component {
    render() {
        return <NoteEditor name="app"></NoteEditor>
    }
}

export default EditPage;
