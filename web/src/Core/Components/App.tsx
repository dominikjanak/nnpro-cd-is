import React from 'react'
import ReactDOM from 'react-dom'

import GlobalStyle from './GlobalStyle'
import { useElement } from '../../Native'
import Header from './Header'

interface Props extends React.ComponentPropsWithoutRef<'div'> {

}

const App = ({ children }: Props) => {

    const { nav } = useElement()

    return (
        <>
            <GlobalStyle />
            {ReactDOM.createPortal(<Header />, nav.current as any)}
            {children}
        </>
    )
}

export default App