import React from 'react'
import Styled from 'styled-components'
import Paginate from 'react-paginate'

import { size, Color, Duration, large, medium } from '../../Style'
import { Segment } from '../types'

interface Static {

}

interface Props extends Omit<React.ComponentPropsWithoutRef<'div'>, 'onChange'> {
    page: Segment
    onChange: (segment: Segment) => void
    itemsCount: number
    freeze?: boolean
}

const PAGE = 'paginator__page'
const BREAK = 'paginator__page--break'
const ACTIVE = 'paginator__page--active'
const EDGE = 'paginator__page--edge'

const Root = Styled.div`
    line-height: 1.5rem;
    user-select: none;
    
    li, ul {
        margin: 0;
        padding: 0;
    }

    .${PAGE}, .${BREAK}, .${EDGE} {
        ${size('2.5rem', '2rem', true)}
        background-color: ${Color.DARKEST};
        display: inline-block;
        overflow: hidden;
        text-align: center;
        transition: background-color: ${Duration.MEDIUM}
        
        &:hover {
            background-color: #2A2A2A;
        }
        
        a {
            ${size()}
            display: block;
            outline: none;
        }
        
        ${medium} {
            width: 2rem;
        }
    }

    .${BREAK} {
        background-color: transparent;
        pointer-events: none;
        width: 0.5rem;
    }
    
    .${ACTIVE} {
        background-color: transparent;
        pointer-events: none;
    }
    
    .${EDGE} {
        ${medium} {
            display: none;
        }
    }
`

const Row = Styled.div`
    margin-top: 0.3rem;
    padding: 0 1rem;
    text-align: right;
    
    &:first-of-type {
        margin-top: 0;
    }
    
    ${large} {
        margin-bottom: 1rem;
        padding: 0;
        text-align: center;
    }
`

const Stats = Styled.p`
    font-size: 85%;
`

const PerPage = Styled.select`
    padding: 0.25rem;
    text-align: center;
`

const Paginator: React.FC<Props> & Static = ({ onChange, page, itemsCount, freeze, ...props }) => {

    const getPagesCount = React.useCallback(() => Math.ceil(itemsCount / page.size), [itemsCount, page.size])
    const getCache = () => ({ pages: getPagesCount(), itemsCount })

    const [cache, setCache] = React.useState(getCache())

    React.useEffect(() => {
        const newPagesCount = getPagesCount()

        if (!freeze && itemsCount > 0) {
            setCache({ pages: newPagesCount, itemsCount })
        }
    }, [itemsCount, page, freeze, getPagesCount])

    const handleChangeSize = (size: number) => {
        if (size !== page.size) {
            onChange({ index: 0, size })
        }
    }

    const handleChangePage = (index: number) => {
        if (index !== page.index) {
            onChange({ index, size: page.size })
        }
    }

    return (
        <Root {...props}>
            <Row>
                <Paginate
                    activeClassName={ACTIVE}
                    breakClassName={BREAK}
                    breakLabel=' '
                    forcePage={page.index}
                    marginPagesDisplayed={1}
                    nextClassName={EDGE}
                    nextLabel='>'
                    onPageChange={page => handleChangePage(page.selected)}
                    pageClassName={PAGE}
                    pageCount={cache.pages}
                    pageRangeDisplayed={5}
                    previousClassName={EDGE}
                    previousLabel='<' />
            </Row>
            <Row>
                <Stats>
                    Zobrazeno {page.index * page.size + 1}-{Math.min(cache.itemsCount, (page.index + 1) * page.size)} z {cache.itemsCount}. Velikost stránky {(
                    <PerPage onChange={event => handleChangeSize(parseInt(event.target.value))} value={page.size}>
                        {[5, 10, 20, 50, 100, 200].map((value, i) => (
                            <option key={i} value={value}>
                                {value}
                            </option>
                        ))}
                    </PerPage>
                )}.
                </Stats>
            </Row>
        </Root>
    )

}

Paginator.defaultProps = {
    freeze: false
}

export default Paginator