/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { StateComponent } from '../../../../../../main/webapp/app/entities/state/state.component';
import { StateService } from '../../../../../../main/webapp/app/entities/state/state.service';
import { State } from '../../../../../../main/webapp/app/entities/state/state.model';

describe('Component Tests', () => {

    describe('State Management Component', () => {
        let comp: StateComponent;
        let fixture: ComponentFixture<StateComponent>;
        let service: StateService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [StateComponent],
                providers: [
                    StateService
                ]
            })
            .overrideTemplate(StateComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StateService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new State(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.states[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
