/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { CompanyLocationComponent } from '../../../../../../main/webapp/app/entities/company-location/company-location.component';
import { CompanyLocationService } from '../../../../../../main/webapp/app/entities/company-location/company-location.service';
import { CompanyLocation } from '../../../../../../main/webapp/app/entities/company-location/company-location.model';

describe('Component Tests', () => {

    describe('CompanyLocation Management Component', () => {
        let comp: CompanyLocationComponent;
        let fixture: ComponentFixture<CompanyLocationComponent>;
        let service: CompanyLocationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [CompanyLocationComponent],
                providers: [
                    CompanyLocationService
                ]
            })
            .overrideTemplate(CompanyLocationComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyLocationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyLocationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new CompanyLocation(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.companyLocations[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
