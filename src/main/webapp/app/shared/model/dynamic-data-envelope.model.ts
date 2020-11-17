import { IAuditInfo } from 'app/shared/model/audit-info.model';
import { IDynamicData } from 'app/shared/model/dynamic-data.model';

export interface IDynamicDataEnvelope {
  id?: number;
  desc?: string;
  auditInfo?: IAuditInfo;
  dynamicData?: IDynamicData[];
}

export class DynamicDataEnvelope implements IDynamicDataEnvelope {
  constructor(public id?: number, public desc?: string, public auditInfo?: IAuditInfo, public dynamicData?: IDynamicData[]) {}
}
