import Language from './Language'
import Url from '../../Routing/Constants/Url'

const CS = Language.CS

export default {

    nav: {
        links: [
            { text: { [CS]: 'Incidenty' }, pathname: Url.INCIDENTS, icon: 'Incidents' },
            { text: { [CS]: 'Přehledy' }, pathname: Url.OVERVIEWS, icon: 'Overviews' }
        ]
    },

    incidents: {
        title: { [CS]: 'Incidenty...' },
    },

    overviews: {
        title: { [CS]: 'Přehledy...' }
    },

    login: {
        name: 'Jméno',
        password: 'Heslo',
        submit: 'Připojit se',
        error: 'Špatné přihlašovací údaje.',
        missingName: 'Napište své jméno',
        missingPassword: 'Napište své heslo',
        title: 'Přihlášení'
    }

}