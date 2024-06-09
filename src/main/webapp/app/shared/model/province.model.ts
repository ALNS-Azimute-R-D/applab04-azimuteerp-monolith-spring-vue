import { type ICountry } from '@/shared/model/country.model';

export interface IProvince {
  id?: number;
  acronym?: string;
  name?: string;
  geoPolygonAreaContentType?: string | null;
  geoPolygonArea?: string | null;
  country?: ICountry;
}

export class Province implements IProvince {
  constructor(
    public id?: number,
    public acronym?: string,
    public name?: string,
    public geoPolygonAreaContentType?: string | null,
    public geoPolygonArea?: string | null,
    public country?: ICountry,
  ) {}
}
