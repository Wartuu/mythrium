import { useEffect, useState } from 'react';
import { API, sendRequest } from '../config/api';
import PropTypes from 'prop-types';

import '../styles/note.scss';
import Spinner from './spinner';

const NoteViewer = ({ uuid }) => {
    const [error, setError] = useState('');
    const [loading, setLoading] = useState('visible');
    const [noteData, setNoteData] = useState('');

    useEffect(() => {
        const controller = new AbortController();
        const signal = controller.signal;
    
        const loadNote = async () => {
            try {    
                let response = await sendRequest(API.NOTE_URL + '/' + uuid, undefined, 'GET', { signal });
    
                if (!response.ok) {
                    setError('Failed to send request');
                    setLoading('hidden');
                    return;
                }
    
                let data = await response.json();
    
                if (data.success === false) {
                    setError(data.information);
                    setLoading('hidden');
                    return;
                }
    
                setNoteData(data.content);
                setLoading('hidden');
            } catch (error) {
                if (error.name !== 'AbortError') {
                    console.error('Error loading note:', error);
                    setError('An error occurred');
                    setLoading('hidden');
                }
            }
        };
    
        loadNote();
    
        return () => controller.abort();
    }, [uuid]);
    

    if (loading === 'visible') {
        return <div className="card-center"><Spinner/></div>;
    }

    if (error) {
        return (
            <>
                <div className="error-color details card-center">{error}</div>
            </>
        );
    }

    return (
        <>
            <div id="note-viewer" className="card-center">
                {noteData}
            </div>
        </>
    );
};

NoteViewer.propTypes = {
    uuid: PropTypes.string
}

export default NoteViewer;
