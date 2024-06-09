import { type ITypeOfVenue } from '@/shared/model/type-of-venue.model';
import { type ICommonLocality } from '@/shared/model/common-locality.model';

export interface IVenue {
  id?: number;
  acronym?: string | null;
  name?: string;
  description?: string | null;
  geoPointLocationContentType?: string | null;
  geoPointLocation?: string | null;
  typeOfVenue?: ITypeOfVenue;
  commonLocality?: ICommonLocality | null;
}

export class Venue implements IVenue {
  constructor(
    public id?: number,
    public acronym?: string | null,
    public name?: string,
    public description?: string | null,
    public geoPointLocationContentType?: string | null,
    public geoPointLocation?: string | null,
    public typeOfVenue?: ITypeOfVenue,
    public commonLocality?: ICommonLocality | null,
  ) {}
}
