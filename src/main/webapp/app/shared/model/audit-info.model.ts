import { Moment } from 'moment';
import { IFileSystem } from 'app/shared/model/file-system.model';
import { IEnvelope } from 'app/shared/model/envelope.model';
import { IConversation } from 'app/shared/model/conversation.model';
import { IDynamicDataEnvelope } from 'app/shared/model/dynamic-data-envelope.model';
import { IDynamicData } from 'app/shared/model/dynamic-data.model';

export interface IAuditInfo {
  id?: number;
  createdDate?: Moment;
  createdBy?: string;
  lastModifiedDate?: Moment;
  lastModifiedBy?: string;
  fileSystem?: IFileSystem;
  envelope?: IEnvelope;
  conversation?: IConversation;
  dynamicDataEnvelope?: IDynamicDataEnvelope;
  dynamicData?: IDynamicData;
}

export class AuditInfo implements IAuditInfo {
  constructor(
    public id?: number,
    public createdDate?: Moment,
    public createdBy?: string,
    public lastModifiedDate?: Moment,
    public lastModifiedBy?: string,
    public fileSystem?: IFileSystem,
    public envelope?: IEnvelope,
    public conversation?: IConversation,
    public dynamicDataEnvelope?: IDynamicDataEnvelope,
    public dynamicData?: IDynamicData
  ) {}
}
