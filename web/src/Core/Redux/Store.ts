import { combineReducers, configureStore } from '@reduxjs/toolkit'

import { Reducer as AuthReducer } from '../../Auth'
import { Reducer as DataReducer } from '../../Data'
import { default as CoreReducer } from './Slice'

const RootReducer = combineReducers({
    auth: AuthReducer,
    data: DataReducer,
    core: CoreReducer
})

const store = configureStore<any>({ reducer: RootReducer })

export default store