import React from 'react'
import { useStrings } from '../../Data'
import { View } from '../../Layout'

interface Props extends React.ComponentPropsWithoutRef<'div'> {

}

const OverviewsView = ({ ...props }: Props) => {

    const strings = useStrings().overviews

    return (
        <View {...props}>
            <h1>
                {strings.title}
            </h1>
        </View>
    )

}

export default OverviewsView