import React from 'react';

function LoadingIndicator({columns, color}) {
    const length = columns != null ? parseInt(columns) : 3;
    const backgroundColor = color != null ? color : "#000";
    const rows = [];
    for (let i=0;i<length;i++) {
        const item = <div className={'spinner-column'} key={i} style={{left: `${8+i*24}px`, animationDelay:`${0-0.12*i}s`, backgroundColor: backgroundColor}}></div>
        rows.push(item);
    }
    return (
        <div className={'spinner-container'}>
            {rows}
        </div>
    );
}

export default LoadingIndicator;