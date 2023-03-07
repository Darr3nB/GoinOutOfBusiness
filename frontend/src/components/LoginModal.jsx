import {utility} from "../utility.js";
import {useNavigate} from "react-router-dom";
import {useState} from "react";

export default function LoginModal({open, closeModal}) {
    if (!open) {
        return null
    }

    const navigate = useNavigate();
    const [loginBtn, setLoginBtn] = useState(false);
    const [loginPtag, setLoginPtag] = useState(false);


    const performLogin = (event) => {
        event.preventDefault();
        const formData = new FormData(event.currentTarget);

        utility.apiPostWithDictionary(`/user/login`,
            {'email': formData.get('username-field'), 'password': formData.get('password-field')})
            .then(response => {
                if (response.ok) {
                    navigate(0);
                }
            })
    }

    const checkEmail = (email) => {
        if (!utility.validateEmail(email)) {
            setLoginPtag(true);
        } else {
            setLoginPtag(false);
        }
    }

    return (
        <div className="modal-overlay">
            <div className="modal-container">
                <div>Login to GOOB</div>
                <p className="closeOnModal" onClick={closeModal}>X</p>
                <form onSubmit={event => performLogin(event)}>
                    <br/><input onInput={(event) => checkEmail(event.target.value)} type="text" placeholder="Email"
                                name="email-field"/>
                    <p className={loginPtag ? 'visible-login-p' : 'invisible-login-p'}>{loginPtag ? 'Invalid e-mail format' : ''}</p>
                    <input type="text" placeholder="Password" name="password-field"/>
                    <br/>
                    <button>Login</button>
                </form>
            </div>
        </div>
    );
}
