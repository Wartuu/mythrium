import React, { Suspense } from "react";
import { RouterProvider, createBrowserRouter } from 'react-router-dom'
import "./styles/global.scss"

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

const App = () => {
    return(
        <Suspense fallback={<div>Loading...</div>}>
            <RouterProvider router={router}/>
        </Suspense>
    )
}

export default App;