import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './pack.reducer';
import { IPack } from 'app/shared/model/pack.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPackUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IPackUpdateState {
  isNew: boolean;
}

export class PackUpdate extends React.Component<IPackUpdateProps, IPackUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { packEntity } = this.props;
      const entity = {
        ...packEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/pack');
  };

  render() {
    const { packEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    const { pixel, pixelContentType } = packEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="frontdeskApp.pack.home.createOrEditLabel">Create or edit a Pack</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : packEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="pack-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    Name
                  </Label>
                  <AvField id="pack-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="nameFrontDeskReceiveLabel" for="nameFrontDeskReceive">
                    Name Front Desk Receive
                  </Label>
                  <AvField id="pack-nameFrontDeskReceive" type="text" name="nameFrontDeskReceive" />
                </AvGroup>
                <AvGroup>
                  <Label id="nameFrontDeskDeliveryLabel" for="nameFrontDeskDelivery">
                    Name Front Desk Delivery
                  </Label>
                  <AvField id="pack-nameFrontDeskDelivery" type="text" name="nameFrontDeskDelivery" />
                </AvGroup>
                <AvGroup>
                  <Label id="namePickupLabel" for="namePickup">
                    Name Pickup
                  </Label>
                  <AvField id="pack-namePickup" type="text" name="namePickup" />
                </AvGroup>
                <AvGroup>
                  <Label id="dateReceivedLabel" for="dateReceived">
                    Date Received
                  </Label>
                  <AvField id="pack-dateReceived" type="date" className="form-control" name="dateReceived" />
                </AvGroup>
                <AvGroup>
                  <Label id="datePickupLabel" for="datePickup">
                    Date Pickup
                  </Label>
                  <AvField id="pack-datePickup" type="date" className="form-control" name="datePickup" />
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="pixelLabel" for="pixel">
                      Pixel
                    </Label>
                    <br />
                    {pixel ? (
                      <div>
                        <a onClick={openFile(pixelContentType, pixel)}>
                          <img src={`data:${pixelContentType};base64,${pixel}`} style={{ maxHeight: '100px' }} />
                        </a>
                        <br />
                        <Row>
                          <Col md="11">
                            <span>
                              {pixelContentType}, {byteSize(pixel)}
                            </span>
                          </Col>
                          <Col md="1">
                            <Button color="danger" onClick={this.clearBlob('pixel')}>
                              <FontAwesomeIcon icon="times-circle" />
                            </Button>
                          </Col>
                        </Row>
                      </div>
                    ) : null}
                    <input id="file_pixel" type="file" onChange={this.onBlobChange(true, 'pixel')} accept="image/*" />
                    <AvInput type="hidden" name="pixel" value={pixel} />
                  </AvGroup>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/pack" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  packEntity: storeState.pack.entity,
  loading: storeState.pack.loading,
  updating: storeState.pack.updating,
  updateSuccess: storeState.pack.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PackUpdate);
