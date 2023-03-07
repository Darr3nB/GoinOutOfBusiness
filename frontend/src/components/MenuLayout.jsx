import {Link} from "react-router-dom";
import LoginModal from "./LoginModal";
import {useState} from "react";

export default function MenuLayout() {
    const [openModal, setOpenModal] = useState(false);

    const showAndHideLoginModal = (event) => {
        event.preventDefault();
        setOpenModal(!openModal);
    }

    return (
        <div>
            <Link to={`/`}>Home</Link>
            <button className="login-btn" onClick={event => showAndHideLoginModal(event)} type="button">Login</button>
            <LoginModal open={openModal} closeModal={() => {
                setOpenModal(false);
            }}/>
        </div>
    );
}
