import LoginModal from "./LoginModal.jsx";
import {Link} from "react-router-dom";
import {useState} from "react";

export default function LoggedOff() {
    const [openModal, setOpenModal] = useState(false);

    const showAndHideLoginModal = (event) => {
        event.preventDefault();
        setOpenModal(!openModal);
    }


    return (
        <span>
            <button className="login-btn button-link margin" onClick={event => showAndHideLoginModal(event)}
                    type="button">Login
            </button>
            <LoginModal open={openModal} closeModal={() => {
                setOpenModal(false);
            }}/>
            <Link to={`/registration`} className="button-link margin">Registration</Link>
        </span>
    );
}
