/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { CompanyEarningComponent } from '../../../../../../main/webapp/app/entities/company-earning/company-earning.component';
import { CompanyEarningService } from '../../../../../../main/webapp/app/entities/company-earning/company-earning.service';
import { CompanyEarning } from '../../../../../../main/webapp/app/entities/company-earning/company-earning.model';

describe('Component Tests', () => {

    describe('CompanyEarning Management Component', () => {
        let comp: CompanyEarningComponent;
        let fixture: ComponentFixture<CompanyEarningComponent>;
        let service: CompanyEarningService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [CompanyEarningComponent],
                providers: [
                    CompanyEarningService
                ]
            })
            .overrideTemplate(CompanyEarningComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyEarningComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyEarningService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new CompanyEarning(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.companyEarnings[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
