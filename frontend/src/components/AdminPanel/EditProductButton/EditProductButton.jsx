import React, {useState} from 'react';
import BaseModal from "../../BaseModal.jsx";
import EditProductForm from "./EditProductForm.jsx";

function EditProductButton({productObject, refreshFunction}) {
    // ToDo Add admin checking

    const [modalIsOpen, setModalIsOpen] = useState(false);
    let modal = modalIsOpen ?
        (<BaseModal title={`Edit ${productObject.name} details`} openStateSetter={setModalIsOpen}>
            <EditProductForm productObject={productObject} refreshFunction={refreshFunction}/>
        </BaseModal>)
        : (<></>)
    return (
        <div className={'edit-product-button'}>
            {/*<img src={imgUrl} alt={'Edit product details'}/>*/}
            <button onClick={()=>setModalIsOpen(!modalIsOpen)}>Edit</button>
            {modal}
        </div>
    );
}

export default EditProductButton;