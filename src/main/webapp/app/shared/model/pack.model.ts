import { Moment } from 'moment';

export interface IPack {
  id?: number;
  name?: string;
  nameFrontDeskReceive?: string;
  nameFrontDeskDelivery?: string;
  namePickup?: string;
  dateReceived?: Moment;
  datePickup?: Moment;
  pixelContentType?: string;
  pixel?: any;
}

export const defaultValue: Readonly<IPack> = {};
