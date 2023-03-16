import {utility} from "../utility.js";
import {useNavigate} from "react-router-dom";
import {useState} from "react";
import Button from "./Button";

export default function LoginModal({open, closeModal}) {
    if (!open) {
        return null
    }

    const navigate = useNavigate();
    const [loginBtn, setLoginBtn] = useState(false);
    const [loginPtag, setLoginPtag] = useState(2);


    const performLogin = async (event) => {
        event.preventDefault();
        const formData = new FormData(event.currentTarget);

        await utility.apiPostWithDictionary(`/user/login`,
            {'email': formData.get('email-field'), 'password': formData.get('password-field')})
            .then(response => {
                if (response.ok) {
                    response.json().then(data => {
                        localStorage.setItem('user', JSON.stringify(data));
                        navigate(0);
                    })
                }
            })
    }

    const checkEmail = (email) => {
        if (email.length === 0) {
            setLoginPtag(2);
            setLoginBtn(false);
        } else if (!utility.validateEmail(email)) {
            setLoginPtag(1);
            setLoginBtn(false);
        } else {
            setLoginPtag(0);
            setLoginBtn(true);
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
                    <p className={loginPtag === 1 ? 'error-email-p' : loginPtag === 0 ? 'valid-email-p' : 'invisible-email-p'}>
                        {loginPtag === 1 ? 'Invalid e-mail format.' : loginPtag === 0 ? 'Valid e-mail format.' : ''}
                    </p>
                    <input type="text" placeholder="Password" name="password-field" minLength="3"/>
                    <br/>
                    <Button text="Login" status={!loginBtn}/>
                </form>
            </div>
        </div>
    );
}
