import { type IDistrict } from '@/shared/model/district.model';

export interface ICommonLocality {
  id?: number;
  acronym?: string;
  name?: string;
  description?: string | null;
  streetAddress?: string;
  houseNumber?: string | null;
  locationName?: string | null;
  postalCode?: string;
  geoPolygonAreaContentType?: string | null;
  geoPolygonArea?: string | null;
  district?: IDistrict;
}

export class CommonLocality implements ICommonLocality {
  constructor(
    public id?: number,
    public acronym?: string,
    public name?: string,
    public description?: string | null,
    public streetAddress?: string,
    public houseNumber?: string | null,
    public locationName?: string | null,
    public postalCode?: string,
    public geoPolygonAreaContentType?: string | null,
    public geoPolygonArea?: string | null,
    public district?: IDistrict,
  ) {}
}
