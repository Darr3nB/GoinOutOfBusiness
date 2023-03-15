import MenuLayout from "../components/MenuLayout";
import Footer from "../components/Footer";
import {Outlet} from "react-router-dom";

export default function Index() {
    return (
        <div className="black">
            <MenuLayout/>
            <Outlet/>
            <Footer/>
        </div>
    );
}
