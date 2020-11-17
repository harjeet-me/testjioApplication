import { IDynamicDataEnvelope } from 'app/shared/model/dynamic-data-envelope.model';
import { IAuditInfo } from 'app/shared/model/audit-info.model';
import { IFileSystem } from 'app/shared/model/file-system.model';
import { IDiscrim } from 'app/shared/model/discrim.model';

export interface IEnvelope {
  id?: number;
  name?: string;
  desc?: string;
  dynamicDataEnvelope?: IDynamicDataEnvelope;
  auditInfo?: IAuditInfo;
  fileSystems?: IFileSystem[];
  envelopes?: IEnvelope[];
  discrim?: IDiscrim;
  owner?: IEnvelope;
}

export class Envelope implements IEnvelope {
  constructor(
    public id?: number,
    public name?: string,
    public desc?: string,
    public dynamicDataEnvelope?: IDynamicDataEnvelope,
    public auditInfo?: IAuditInfo,
    public fileSystems?: IFileSystem[],
    public envelopes?: IEnvelope[],
    public discrim?: IDiscrim,
    public owner?: IEnvelope
  ) {}
}
