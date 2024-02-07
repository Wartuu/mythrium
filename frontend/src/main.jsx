import React from 'react'
import ReactDOM from 'react-dom/client'
import './styles/global.scss'
import App from './app'


// initialize website

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <App/>
    </React.StrictMode>,
)
