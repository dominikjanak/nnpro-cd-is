import React from 'react'
import ReactDOM from 'react-dom'
import { Router, Switch, Route, Redirect } from 'react-router-dom'
import { Provider } from 'react-redux'

import * as serviceWorker from './serviceWorker'
import { App, IncidentsView, OverviewsView, Store } from './Core'
import { History, Url } from './Routing'

ReactDOM.render(
    <Provider store={Store}>
        <Router history={History}>
            <App>
                <Switch>
                    <Route exact path={Url.INCIDENTS} component={IncidentsView} />
                    <Route exact path={Url.OVERVIEWS} component={OverviewsView} />
                    <Redirect to={Url.INCIDENTS} />
                </Switch>
            </App>
        </Router>
    </Provider>, document.getElementById('app')
)

serviceWorker.unregister()
