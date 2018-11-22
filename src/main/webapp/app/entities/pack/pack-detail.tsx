import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './pack.reducer';
import { IPack } from 'app/shared/model/pack.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPackDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class PackDetail extends React.Component<IPackDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { packEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Pack [<b>{packEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{packEntity.name}</dd>
            <dt>
              <span id="nameFrontDeskReceive">Name Front Desk Receive</span>
            </dt>
            <dd>{packEntity.nameFrontDeskReceive}</dd>
            <dt>
              <span id="nameFrontDeskDelivery">Name Front Desk Delivery</span>
            </dt>
            <dd>{packEntity.nameFrontDeskDelivery}</dd>
            <dt>
              <span id="namePickup">Name Pickup</span>
            </dt>
            <dd>{packEntity.namePickup}</dd>
            <dt>
              <span id="dateReceived">Date Received</span>
            </dt>
            <dd>
              <TextFormat value={packEntity.dateReceived} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="datePickup">Date Pickup</span>
            </dt>
            <dd>
              <TextFormat value={packEntity.datePickup} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="pixel">Pixel</span>
            </dt>
            <dd>
              {packEntity.pixel ? (
                <div>
                  <a onClick={openFile(packEntity.pixelContentType, packEntity.pixel)}>
                    <img src={`data:${packEntity.pixelContentType};base64,${packEntity.pixel}`} style={{ maxHeight: '30px' }} />
                  </a>
                  <span>
                    {packEntity.pixelContentType}, {byteSize(packEntity.pixel)}
                  </span>
                </div>
              ) : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/pack" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/pack/${packEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ pack }: IRootState) => ({
  packEntity: pack.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PackDetail);
