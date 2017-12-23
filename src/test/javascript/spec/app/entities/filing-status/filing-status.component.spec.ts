/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { FilingStatusComponent } from '../../../../../../main/webapp/app/entities/filing-status/filing-status.component';
import { FilingStatusService } from '../../../../../../main/webapp/app/entities/filing-status/filing-status.service';
import { FilingStatus } from '../../../../../../main/webapp/app/entities/filing-status/filing-status.model';

describe('Component Tests', () => {

    describe('FilingStatus Management Component', () => {
        let comp: FilingStatusComponent;
        let fixture: ComponentFixture<FilingStatusComponent>;
        let service: FilingStatusService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [FilingStatusComponent],
                providers: [
                    FilingStatusService
                ]
            })
            .overrideTemplate(FilingStatusComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FilingStatusComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FilingStatusService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new FilingStatus(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.filingStatuses[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
