import React from 'react'
import ReactDOM from 'react-dom/client'
import { RouterProvider, createBrowserRouter } from 'react-router-dom'
import EditPage from './routes/edit'
import HomeRoute from './routes/home'


// initialize website



const router = createBrowserRouter([
    {
        'path': '/',
        element: (
            <HomeRoute/>
        )
    },
    {
        'path': '/note/edit',
        element: (
            <EditPage/>
        )
    }
])  


ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <RouterProvider router={router}/>
    </React.StrictMode>,
)
