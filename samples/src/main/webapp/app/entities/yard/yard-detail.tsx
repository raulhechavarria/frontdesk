import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './yard.reducer';
import { IYard } from 'app/shared/model/yard.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IYardDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class YardDetail extends React.Component<IYardDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { yardEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Yard [<b>{yardEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="streetandnumber">Streetandnumber</span>
            </dt>
            <dd>{yardEntity.streetandnumber}</dd>
            <dt>
              <span id="city">City</span>
            </dt>
            <dd>{yardEntity.city}</dd>
            <dt>
              <span id="frequenceSummer">Frequence Summer</span>
            </dt>
            <dd>{yardEntity.frequenceSummer}</dd>
            <dt>
              <span id="frequenceWinter">Frequence Winter</span>
            </dt>
            <dd>{yardEntity.frequenceWinter}</dd>
            <dt>
              <span id="dateDone">Date Done</span>
            </dt>
            <dd>
              <TextFormat value={yardEntity.dateDone} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>Customer</dt>
            <dd>{yardEntity.customer ? yardEntity.customer.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/yard" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/yard/${yardEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ yard }: IRootState) => ({
  yardEntity: yard.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(YardDetail);
