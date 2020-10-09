import { Pageable, Redux } from '../../Data'
import { Requests } from '../../Async'
import {Incident, IncidentNew} from "../types";

const Slice = Redux.slice(
    'core',
    {
        incidents: Redux.async<Pageable<Incident>>(),
        newIncident: Redux.async<Incident>(),
        removedIncident: Redux.async<void>()
    },
    ({ async }) => ({
        getIncidents: async<void, Pageable<Incident>>('incidents', () => Requests.get<Pageable<Incident>>('incidents')),

        addIncident: async<IncidentNew, Incident>('newIncident', incident => Requests.post<Incident>('incidents', incident), {
            onSuccess: (state, action) => {
                state.incidents.payload!.content.push(action.payload)
                state.incidents.payload!.totalElements++
            }
        }),

        removeIncident: async<string, void>('removedIncident', incidentId => Requests.delete<any>(`incidents/${incidentId}`), {
            onSuccess: (state, action) => {
                state.incidents.payload!.content = state.incidents.payload!.content.filter(incident => incident.id !== action.meta!.arg)
                state.incidents.payload!.totalElements--
            }
        })

    })
)

export default Slice.reducer

export const { getIncidents, addIncident, removeIncident } = Slice.actions