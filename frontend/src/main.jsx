/* disabled warns for react-refresh cause of router not being often updated, todo: fix */
import React from 'react'
import ReactDOM from 'react-dom/client'
import "./styles/global.scss"
import App from './app'


// initialize website

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <App/>
    </React.StrictMode>,
)
