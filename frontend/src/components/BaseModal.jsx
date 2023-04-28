import React from 'react';

function BaseModal({title, openStateSetter, extraModalClassName, extraTitleClassName, children}) {
        return (
        <div className={`modal-container ${extraModalClassName}`}>
            <div className={`modal-title ${extraTitleClassName}`}>{title}</div>
            <p className="closeOnModal" onClick={() => {openStateSetter(false)}}>X</p>
            <div className={'modal-content'}>
                {children}
            </div>
        </div>
    );
}

export default BaseModal;