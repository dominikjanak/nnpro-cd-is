import React from 'react'
import { useStrings } from '../../Data'
import { useIncidents } from '..'
import { View } from '../../Layout'

interface Props extends React.ComponentPropsWithoutRef<'div'> {

}

const IncidentsView = ({ ...props }: Props) => {

    const strings = useStrings().incidents
    const incidents = useIncidents()

    return (
        <View {...props}>
            <h1>
                {strings.title}
            </h1>
            {JSON.stringify(incidents.payload)}
        </View>
    )

}

export default IncidentsView