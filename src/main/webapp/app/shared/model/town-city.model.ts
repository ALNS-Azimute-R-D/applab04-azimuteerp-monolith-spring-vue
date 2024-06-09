import { type IProvince } from '@/shared/model/province.model';

export interface ITownCity {
  id?: number;
  acronym?: string;
  name?: string;
  geoPolygonAreaContentType?: string | null;
  geoPolygonArea?: string | null;
  province?: IProvince;
}

export class TownCity implements ITownCity {
  constructor(
    public id?: number,
    public acronym?: string,
    public name?: string,
    public geoPolygonAreaContentType?: string | null,
    public geoPolygonArea?: string | null,
    public province?: IProvince,
  ) {}
}
