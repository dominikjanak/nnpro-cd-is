import { useSelector } from 'react-redux'

import { AsyncData, Pageable } from '../../Data'
import { Incident } from '../types'

type State = any // TODO: Use typeof State.

export const useIncidents = (): AsyncData<Pageable<Incident>> => useSelector((state: State) => state.core.incidents)