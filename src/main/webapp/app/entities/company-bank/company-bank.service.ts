import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { CompanyBank } from './company-bank.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CompanyBankService {

    private resourceUrl = SERVER_API_URL + 'api/company-banks';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(companyBank: CompanyBank): Observable<CompanyBank> {
        const copy = this.convert(companyBank);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(companyBank: CompanyBank): Observable<CompanyBank> {
        const copy = this.convert(companyBank);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<CompanyBank> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to CompanyBank.
     */
    private convertItemFromServer(json: any): CompanyBank {
        const entity: CompanyBank = Object.assign(new CompanyBank(), json);
        entity.createdDate = this.dateUtils
            .convertLocalDateFromServer(json.createdDate);
        return entity;
    }

    /**
     * Convert a CompanyBank to a JSON which can be sent to the server.
     */
    private convert(companyBank: CompanyBank): CompanyBank {
        const copy: CompanyBank = Object.assign({}, companyBank);
        copy.createdDate = this.dateUtils
            .convertLocalDateToServer(companyBank.createdDate);
        return copy;
    }
}
