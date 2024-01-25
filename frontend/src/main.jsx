/* disabled warns for react-refresh cause of router not being often updated, todo: fix */
import React, { Suspense } from 'react'
import ReactDOM from 'react-dom/client'
import { RouterProvider, createBrowserRouter } from 'react-router-dom'
import "./styles/global.scss"

// initialize website

const HomePage      =   React.lazy(() => import('./routes/home'))
const Error404      =   React.lazy(() => import('./routes/error404'))
const LoginPage     =   React.lazy(() => import('./routes/login'))
const RegisterPage  =   React.lazy(() => import('./routes/register'))

const router = createBrowserRouter([
    {
        'path': '/',
        element: <HomePage/>
    },
    {
        'path': '/login',
        element: <LoginPage/>
        
    },
    {
        'path': '/register',
        element: <RegisterPage/>
        
    },
    {
        'path': "*",
        element: <Error404/>
        
    }
])  

console.log("abcd");

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <Suspense fallback={<div>Loading...</div>}>
            <RouterProvider router={router}/>
        </Suspense>
    </React.StrictMode>,
)
