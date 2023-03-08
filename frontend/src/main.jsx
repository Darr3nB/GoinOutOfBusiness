import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import Index from "./routes";
import HomePage from "./components/HomePage";
import Registration from "./routes/Registration";
import ZSooS from "./components/ZSooS";
import Darren from "./components/Darren";
import ProductView from "./routes/productView.jsx";
import Profile from "./routes/Profile";

const router = createBrowserRouter([
    {
        path: "/",
        element: <Index/>,
        children: [
            {
                index: true,
                element: <HomePage/>
            },
            {
                path: "/registration",
                element: <Registration/>
            },
            {
                path: "/developers",
                children: [
                    {
                        path: "zsoos",
                        element: <ZSooS/>
                    },
                    {
                        path: "darren",
                        element: <Darren/>
                    }
                ]
            },
            {
                path:"/view-product/:productId",
                element: <ProductView/>
            },
            {
                path: "/profile",
                element: <Profile/>
            }
        ]
    }
])

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <RouterProvider router={router}/>
    </React.StrictMode>,
)
