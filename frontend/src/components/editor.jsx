import React from "react";
import PropTypes from "prop-types";

class NoteEditor extends React.Component {
    render() {
        return <h1>testing: {this.props.name}</h1>
    }

}


NoteEditor.propTypes = {
    name: PropTypes.string.isRequired,
}

export default NoteEditor;