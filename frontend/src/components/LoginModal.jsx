export default function LoginModal({open, closeModal}) {
    if (!open) {
        return null
    }

    return (
        <div className="modal-overlay">
            <div className="modal-container">
                <div>Login to GOOB</div>
                <p className="closeOnModal" onClick={closeModal}>X</p>
                <br/><input type="text" placeholder="Username"/>
                <input type="text" placeholder="Password"/>
                <br/>
                <button type="button">Login</button>
            </div>
        </div>
    );
}
