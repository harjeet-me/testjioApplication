import { IAuditInfo } from 'app/shared/model/audit-info.model';
import { IDynamicDataEnvelope } from 'app/shared/model/dynamic-data-envelope.model';
import { DataType } from 'app/shared/model/enumerations/data-type.model';

export interface IDynamicData {
  id?: number;
  dataKey?: string;
  dataValue?: string;
  valueDataType?: DataType;
  auditInfo?: IAuditInfo;
  dynamicDataEnvelope?: IDynamicDataEnvelope;
}

export class DynamicData implements IDynamicData {
  constructor(
    public id?: number,
    public dataKey?: string,
    public dataValue?: string,
    public valueDataType?: DataType,
    public auditInfo?: IAuditInfo,
    public dynamicDataEnvelope?: IDynamicDataEnvelope
  ) {}
}
