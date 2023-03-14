import {useNavigate} from "react-router-dom";
import {utility} from "../utility.js";
import {useState} from "react";
import Button from "../components/Button";

export default function Registration() {
    const navigate = useNavigate();
    const [uploadedImage, setImage] = useState(null);
    const [regBtn, setRegBtn] = useState(0);
    const [emailPtagText, setEmailPtagText] = useState("");


    function passwordMatcher(password, password2) {
        return password === password2;
    }

    const performRegistration = async (event) => {
        event.preventDefault();
        console.log(uploadedImage);
        // const formData = new FormData(event.currentTarget);
        // const profilePicture = uploadedImage === null ? utility.questionMarkPicture : uploadedImage;
        //
        // if (!passwordMatcher(formData.get("password-field"), formData.get("password-again-field"))) {
        //     alert("Passwords must match!");
        //     return;
        // }
        //
        // await utility.apiPostWithDictionary(`/user/registration`, {
        //     'email': formData.get("email-field"),
        //     'password': formData.get("password-field"),
        //     'passwordAgain': formData.get("password-again-field"),
        //     'dateOfBirth': null,
        //     'profilePicture': profilePicture,
        // }).then(response => {
        //     if (response.ok) {
        //         navigate(0);
        //     }
        // })
    }

    const uploadImage = async (event) => {
        const file = event.target.files[0];
        const base64 = await utility.convertBase64(file);

        setImage(base64);
    }

    const checkEmail = async (email) => {
        if (email.length <= 0) {
            setEmailPtagText("");
            setRegBtn(0);
            return;
        }

        if (!utility.validateEmail(email)) {
            setEmailPtagText("Invalid e-mail or is already registered.");
            setRegBtn(0);
            return;
        }

        await utility.apiGet(`/user/validate-email-for-register/${email}`)
            .then(response => {
                if (response.ok) {
                    setEmailPtagText("E-mail available.");
                    setRegBtn(1);
                } else if (response.status === 403) {
                    setEmailPtagText("Invalid e-mail or is already registered.");
                    setRegBtn(0);
                }
            });
    }

    return (
        <div>
            <h1 className="middle-text">Register to goob.hu</h1>
            <div className="reg-form">
                <form onSubmit={event => performRegistration(event)}>
                    <input type="text" placeholder="Email address" name="email-field"
                           onInput={event => checkEmail(event.target.value)} minLength="3"/>
                    <p className={regBtn === 1 ? 'valid-email-p' : regBtn === 0 ? 'error-email-p' : 'invisible-email-p'}>{emailPtagText}</p>
                    <input type="text" placeholder="Password" name="password-field" minLength="3"/>
                    <input type="text" placeholder="Password again" name="password-again-field" minLength="3"/>
                    {/*TODO Date of birth*/}
                    <input type="file" accept=".jpg, .jpeg, .png" onChange={event => uploadImage(event)}/>
                    <Button text="Registration" status={!regBtn}/>
                </form>
            </div>
        </div>
    );
}
